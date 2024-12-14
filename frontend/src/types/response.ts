export interface ApiResponse<T = any> {
    success?: boolean;
    message: string;
    data?: T;
}

export interface ErrorResponse {
    code: number;
    message: string;
    target: Record<string, any>;
}