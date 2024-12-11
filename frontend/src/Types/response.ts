// src/types/response.ts
export interface ApiResponse<T = any> {
    code?: number;
    message: string;
    target?: Record<string, any>;
    data?: T;
    success?: boolean;
}