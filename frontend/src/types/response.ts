export interface ApiResponse<T = any> {
    success?: boolean;
    message: string;
    data?: T;
}

export interface ErrorResponse<T = any> {
    code?: number;
    message: string;
    target?: Record<string, any>;
}