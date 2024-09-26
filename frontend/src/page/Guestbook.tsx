import React, {useEffect, useState} from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from '@mui/material';
import {ArrowDropDown} from '@mui/icons-material';
import axios, {AxiosResponse} from "axios";
import {format, parseISO} from "date-fns";
import BoardDetail from "@component/BoardDetail";
import Typography from "@mui/material/Typography";

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

interface BoardData {
    title: string,
    writer: string,
    content: string,
}

const formatDate = (dateString: string): string => {
    const date = parseISO(dateString);
    return format(date, 'yyyy-MM-dd');
};

const Guestbook = (): React.ReactElement => {
    const [rows, setRows] = useState<RowData[]>([]);
    const [boardData, setBoardData] = useState<BoardData | null>(null);
    // const [totalCount, setTotalCount] = useState<number>(0);
    // const [currentPage, setCurrentPage] = useState<number>(1);
    // const [pageSize, setPageSize] = useState<number>(10);

    const boardClick = (row: RowData) => {
        setBoardData({
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
            <Accordion defaultExpanded>
                <AccordionSummary
                    expandIcon={<ArrowDropDown/>}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    <Typography variant={'h4'}>Guestbook</Typography>
                </AccordionSummary>
                <AccordionDetails>
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
                                        onClick={() => {
                                            boardClick(row)
                                        }}
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
                </AccordionDetails>
            </Accordion>
            {
                boardData &&
                <Paper elevation={1} sx={{p: 3,}}>
                    <BoardDetail board={boardData}/>
                </Paper>
            }
        </div>
    );
}

export default Guestbook;