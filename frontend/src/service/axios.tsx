import axios, {AxiosError} from 'axios';
import {ErrorResponse } from '@/types/response';

const instance = axios.create({
    timeout: 5000,
});

instance.interceptors.response.use(
    (response) => response,
    (error: AxiosError<ErrorResponse>) => {
        const errorResponse = error.response?.data;

        if (errorResponse) {
            return Promise.reject({
                ...errorResponse,
                message: errorResponse.message || '알 수 없는 에러가 발생했습니다.'
            });
        }

        return Promise.reject({
            code: error.code || 500,
            message: error.message || '서버와 통신할 수 없습니다.',
            target: {}
        });
    }
);

export default instance;