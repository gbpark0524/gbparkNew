import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import WriteForm from '@component/WriteForm';
import axios from 'axios';

interface WriteFormData {
    title: string;
    writer: string;
    email: string;
    password: string;
    content: string;
    secret: boolean;
}

const FormGuestbook = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [initialData, setInitialData] = useState<Partial<WriteFormData>>();

    useEffect(() => {
        if (id) {
            // 기존 게시글 데이터 로드
            axios.get(`/board/guestbook/${id}`)
                .then(response => {
                    if (response.data.success) {
                        setInitialData(response.data.data);
                    }
                })
                .catch(error => {
                    console.error('게시글 로드 중 오류 발생:', error);
                    alert('게시글을 불러올 수 없습니다.');
                    navigate('/guestbook');
                });
        }
    }, [id]);

    const handleSubmit = async (formData: WriteFormData) => {
        
        try {
            const response = await axios.post('/board/guestbook', formData);
            console.log('서버 응답:', response.data);
            alert('방명록이 등록되었습니다.');
            navigate('/guestbook');
        } catch (error) {
            console.error('방명록 등록 중 오류 발생:', error);
            alert('방명록 등록 중 오류가 발생했습니다. 다시 시도해 주세요.');
        }
    };

    const handleCancel = () => {
        navigate('/guestbook');
    };

    return (
        <WriteForm
            onSubmit={handleSubmit}
            onCancel={handleCancel}
            initialData={initialData}
        />
    );
};

export default FormGuestbook;