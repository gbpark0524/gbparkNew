import React from 'react';
import {useNavigate} from 'react-router-dom';
import WriteForm from '@component/WriteForm';

interface WriteFormData {
    title: string;
    writer: string;
    email: string;
    password: string;
    content: string;
}

const FormGuestbook = () => {
    const navigate = useNavigate();

    const handleSubmit = (formData: WriteFormData) => {
        console.log('Form submitted:', formData);
        navigate('/guestbook');
    };

    const handleCancel = () => {
        navigate('/guestbook');
    };

    return (
        <WriteForm
            onSubmit={handleSubmit}
            onCancel={handleCancel}
        />
    );
};

export default FormGuestbook;