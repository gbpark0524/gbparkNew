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

const ModalState = {
    CLOSE: 'CLOSE',
    MODIFY: 'MODIFY',
    DELETE: 'DELETE'
} as const;

type ModalStateType = typeof ModalState[keyof typeof ModalState];

interface BoardDetailProps {
    board: {
        title: string,
        writer: string,
        content: string,
    }
    onClose: () => void;
}

const BoardDetail = ({board, onClose}: BoardDetailProps) => {
    const [pwModal, setPwModal] = useState<ModalStateType>(ModalState.CLOSE);
    const [detailState, setDetailState] = useState(true);

    const closeDetail = () => {
        setDetailState(false);
        onClose();
    }

    const delOpen = () => {
        setPwModal(ModalState.DELETE);
    };

    const modOpen = () => {
        setPwModal(ModalState.MODIFY);
    };

    const pwClose = () => {
        setPwModal(ModalState.CLOSE);
    };

    const modalButtons = {
        [ModalState.DELETE]: (
            <Button variant={"contained"} color={"error"} onClick={pwClose}>DELETE</Button>
        ),
        [ModalState.MODIFY]: (
            <Button variant={"contained"} color={"info"} onClick={pwClose}>MODIFY</Button>
        ),
        [ModalState.CLOSE]: null
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
                        open={pwModal !== ModalState.CLOSE}
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
                            {modalButtons[pwModal]}
                        <Button variant={"outlined"} color={"secondary"} onClick={pwClose}>NO</Button>
                    </DialogActions>
                </Dialog>
                </Paper>}
        </div>
    );
}

export default BoardDetail;
