import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

interface RowData {
    id: number;
    title: string;
    author: string;
    date: string;
}

function createData(id: number, title: string, author: string, date: string): RowData {
    return { id, title, author, date};
}

const rows: RowData[] = [
    createData(1, '첫 번째 게시글입니다.', '홍길동', '2023-05-01'),
    createData(2, '두 번째 게시글입니다.', '김철수', '2023-05-02'),
    createData(3, '세 번째 게시글입니다.', '이영희', '2023-05-03'),
    // ... 더 많은 게시글 데이터
];

const Guestbook = (): React.ReactElement => {
    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>번호</TableCell>
                        <TableCell align="left">제목</TableCell>
                        <TableCell align="right">작성자</TableCell>
                        <TableCell align="right">작성일</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row: RowData) => (
                        <TableRow
                            key={row.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {row.id}
                            </TableCell>
                            <TableCell align="left">{row.title}</TableCell>
                            <TableCell align="right">{row.author}</TableCell>
                            <TableCell align="right">{row.date}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}

export default Guestbook;