import React, {useEffect, useState} from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@mui/material';
import axios, {AxiosResponse} from "axios";
import {parseISO, format} from "date-fns";
import BoardDetail from "@component/BoardDetail";

interface PaginatedResponse {
    page: number;
    size: number;
    totalCount: number;
    items: RowData[];
}

interface RowData {
    id: number;
    title: string;
    content: string;
    writer: string;
    date: string;
}

interface BoardDetail {
    title: string,
    writer: string,
    content: string,
}

const boardCont : BoardDetail =  {
    title : 'title',
    writer : 'writer',
    content : 'content',
}

const formatDate = (dateString: string): string => {
    const date = parseISO(dateString);
    return format(date, 'yyyy-MM-dd');
};

const Guestbook = (): React.ReactElement => {
    const [rows, setRows] = useState<RowData[]>([]);
    const [boardDetail, setBoardDetail] = useState<BoardDetail | null>(null);
    // const [totalCount, setTotalCount] = useState<number>(0);
    // const [currentPage, setCurrentPage] = useState<number>(1);
    // const [pageSize, setPageSize] = useState<number>(10);

    const boardClick = (row: RowData) => {
        setBoardDetail({
            title: row.title,
            writer: row.writer,
            content: row.content
        });
    };
    
    useEffect(() => {
        axios.get<PaginatedResponse>('/board/guestbooks')
            .then((response: AxiosResponse<PaginatedResponse>) => {
                if (response.data && Array.isArray(response.data.items)) {
                    const formattedRows = response.data.items.map(row => ({
                        ...row,
                        date: formatDate(row.date)
                    }));
                    setRows(formattedRows);
                    // setTotalCount(response.data.totalCount);
                    // setCurrentPage(response.data.page);
                    // setPageSize(response.data.size);
                } else {
                    console.error('Received data is not in the expected format');
                }
            })
            .catch((error) => {
                console.error('Error fetching guestbooks:', error);
            });
    }, []);

    return (
        <div>
            
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 650}} aria-label="simple table">
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
                                sx={{'&:last-child td, &:last-child th': {border: 0}, cursor: 'pointer'}}
                                onClick={() => {boardClick(row)}}
                            >
                                <TableCell component="th" scope="row">
                                    {row.id}
                                </TableCell>
                                <TableCell align="left">{row.title}</TableCell>
                                <TableCell align="right">{row.writer}</TableCell>
                                <TableCell align="right">{row.date}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            {boardDetail && <BoardDetail board={boardDetail} />}
        </div>
    );
}

export default Guestbook;