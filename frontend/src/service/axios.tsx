import axios, {AxiosError, AxiosResponse} from 'axios';
import { ApiResponse, ErrorResponse } from '@/types/response';

const instance = axios.create();

instance.interceptors.response.use(
    (response) => response,
    (error: AxiosError<ErrorResponse>) => {
        const errorData = error.response?.data;
        const errorMessage = errorData?.message || '알 수 없는 에러가 발생했습니다.';

        return Promise.reject({
            message: errorMessage,
            code: errorData?.code,
            target: errorData?.target
        });
    }
);

export default instance;