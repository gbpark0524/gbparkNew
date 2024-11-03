import React, {useState} from 'react';
import styles from '@assets/styles/Board.module.scss';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle, Paper,
    Stack,
    TextField
} from '@mui/material';
import {Delete, Edit, Close} from '@mui/icons-material';
import IconButton from "@mui/material/IconButton";

interface BoardDetailProps {
    board: {
        title: string,
        writer: string,
        content: string,
    }
    onClose: () => void;
}

const BoardDetail = ({board, onClose}: BoardDetailProps) => {
    const CLOSE = 0;
    const MODIFY = 1;
    const DELETE = 2;
    const [pwModal, setPwModal] = useState(CLOSE);
    const [detailState, setDetailState] = useState(true);
    
    const closeDetail = () => {
        setDetailState(false);
        onClose();
    }

    const delOpen = () => {
        setPwModal(DELETE);
    };

    const modOpen = () => {
        setPwModal(MODIFY);
    };

    const pwClose = () => {
        setPwModal(CLOSE);
    };

    return (
        <div>
            {detailState &&
                <Paper elevation={1} sx={{p: 3,}}>
                    <Stack spacing={2} direction="column">
                        <div className={styles['content']}>
                            <div className={styles['head']}>
                                <div className={styles['area-close']}>
                                    <IconButton
                                        size="large"
                                        aria-label="close detail window of a board"
                                        onClick={closeDetail}
                                        edge={"end"}
                                    >
                                        <Close></Close>
                                    </IconButton>
                                </div>
                                <div className={styles['title']}>{board.title}</div>
                                <div className={styles['writer']}>{board.writer}</div>
                            </div>
                            <div className={styles['cont-board']}>
                                {board.content}
                            </div>
                        </div>
                        <Stack spacing={2} direction="row" justifyContent="flex-end">
                            <Button variant="outlined" startIcon={<Edit/>} onClick={modOpen}>
                                Edit
                            </Button>
                            <Button variant="contained" color="error" startIcon={<Delete/>} onClick={delOpen}>
                                Delete
                            </Button>
                        </Stack>
                    </Stack>
                    <Dialog
                        open={pwModal !== CLOSE}
                        onClose={pwClose}
                        PaperProps={{
                            component: 'form',
                            onSubmit: (event: React.FormEvent<HTMLFormElement>) => {
                                event.preventDefault();
                                const formData = new FormData(event.currentTarget);
                                const formJson = Object.fromEntries((formData as any).entries());
                                const email = formJson.email;
                                console.log(email);
                                pwClose();
                            },
                        }}
                    >
                        <DialogTitle>Insert Password</DialogTitle>
                        <DialogContent>
                            <DialogContentText>
                            </DialogContentText>
                            <TextField
                                autoFocus
                                required
                                margin="dense"
                                id="name"
                                name="email"
                                label="Password"
                                type="email"
                                fullWidth
                                variant="standard"
                            />
                        </DialogContent>
                        <DialogActions>
                            {pwModal === DELETE &&
                                <Button variant={"contained"} color={"error"} onClick={pwClose}>DELETE</Button>}
                            {pwModal === MODIFY &&
                                <Button variant={"contained"} color={"info"} onClick={pwClose}>MODIFY</Button>}
                            <Button variant={"outlined"} color={"secondary"} onClick={pwClose}>NO</Button>
                        </DialogActions>
                    </Dialog>
                </Paper>}
        </div>
    );
}

export default BoardDetail;
