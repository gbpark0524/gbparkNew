import React from 'react';
import styles from '@assets/styles/Board.module.scss';
import {Button, Stack} from '@mui/material';
import {Delete, Edit} from '@mui/icons-material';

interface BoardDetailProps {
    board: {
        title: string,
        writer: string,
        content: string,
    }
}

const BoardDetail = ({board}: BoardDetailProps) => {
    return (
        <Stack spacing={2} direction="column">
            <div className={styles['content']}>
                <div className={styles['head']}>
                    <div className={styles['area-empty']}></div>
                    <div className={styles['title']}>{board.title}</div>
                    <div className={styles['writer']}>{board.writer}</div>
                </div>
                <div className={styles['cont-board']}>
                    {board.content}
                </div>
            </div>
            <Stack spacing={2} direction="row" justifyContent="flex-end">
                <Button variant="outlined" startIcon={<Edit/>}>
                    Edit
                </Button>
                <Button variant="contained" color="error" startIcon={<Delete/>}>
                    Delete
                </Button>
            </Stack>
        </Stack>
    );
}

export default BoardDetail;
