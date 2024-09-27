import React, {useState} from 'react';
import {Box, Button, Grid, IconButton, InputAdornment, Paper, TextField, Typography} from '@mui/material';
import {Visibility, VisibilityOff} from '@mui/icons-material';

interface WriteFormData {
    title: string;
    writer: string;
    email: string;
    password: string;
    content: string;
}

interface WriteFormProps {
    onSubmit: (formData: WriteFormData) => void;
    onCancel: () => void;
    initialData?: Partial<WriteFormData>;
}

const WriteForm = ({onSubmit, onCancel, initialData = {}}: WriteFormProps) => {
    const [formData, setFormData] = useState<WriteFormData>({
        title: initialData.title || '',
        writer: initialData.writer || '',
        email: initialData.email || '',
        password: initialData.password || '',
        content: initialData.content || ''
    });

    const [showPassword, setShowPassword] = useState(false);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = event.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        onSubmit(formData);
    };

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <Paper elevation={3} sx={{p: 3, maxWidth: 600, mx: 'auto', mt: 4}}>
            <Typography variant="h5" component="h2" gutterBottom>
                글쓰기
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="title"
                    label="제목"
                    name="title"
                    autoFocus
                    value={formData.title}
                    onChange={handleChange}
                />
                <Grid container spacing={2}>
                    <Grid item xs={8}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="writer"
                            label="작성자"
                            name="writer"
                            value={formData.writer}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={4}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="비밀번호"
                            type={showPassword ? 'text' : 'password'}
                            id="password"
                            autoComplete="current-password"
                            value={formData.password}
                            onChange={handleChange}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle password visibility"
                                            onClick={handleClickShowPassword}
                                            edge="end"
                                        >
                                            {showPassword ? <VisibilityOff/> : <Visibility/>}
                                        </IconButton>
                                    </InputAdornment>
                                )
                            }}
                        />
                    </Grid>
                </Grid>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="email"
                    label="이메일"
                    name="email"
                    type="email"
                    autoComplete="email"
                    value={formData.email}
                    onChange={handleChange}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="content"
                    label="내용"
                    id="content"
                    multiline
                    rows={4}
                    value={formData.content}
                    onChange={handleChange}
                />
                <Box sx={{display: 'flex', justifyContent: 'space-between', mt: 3}}>
                    <Button
                        variant="outlined"
                        onClick={onCancel}
                    >
                        취소
                    </Button>
                    <Button
                        type="submit"
                        variant="contained"
                    >
                        작성
                    </Button>
                </Box>
            </Box>
        </Paper>
    );
};

export default WriteForm;