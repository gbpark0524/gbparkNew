import React, {useEffect, useState} from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Fab,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TablePagination,
    TableRow,
    Typography
} from '@mui/material';
import {ArrowDropDown, Create, Lock} from '@mui/icons-material';
import axios from "axios";
import {format, parseISO} from "date-fns";
import BoardDetail from "@component/BoardDetail";
import {useNavigate} from 'react-router-dom';
import TablePaginationActions from "@mui/material/TablePagination/TablePaginationActions";
import IconButton from "@mui/material/IconButton";

interface PaginatedResponse {
    page: number;
    size: number;
    totalCount: number;
    totalPage: number;
    items: RowData[];
}

interface RowData {
    id: number;
    title: string;
    content: string;
    writer: string;
    date: string;
    secret: boolean;
}

interface BoardData {
    id: number,
    title: string,
    writer: string,
    content: string,
}

const formatDate = (dateString: string): string => {
    const date = parseISO(dateString);
    return format(date, 'yyyy-MM-dd');
};

const Guestbook = (): React.ReactElement => {
    const navigate = useNavigate();
    const [rows, setRows] = useState<RowData[]>([]);
    const [boardData, setBoardData] = useState<BoardData | null>(null);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [totalCount, setTotalCount] = useState(0);
    const [selectedId, setSelectedId] = useState<number | null>(null);

    const boardClick = (row: RowData) => {
        if (!row.secret) {
            setBoardData({
                id: row.id,
                title: row.title,
                writer: row.writer,
                content: row.content
            });
            setSelectedId(row.id);
        }
    };

    const deselectBoard = () => {
        setBoardData(null);
        setSelectedId(null);  // 선택된 셀 제거
    };

    useEffect(() => {
        let isMounted = true;

        const fetchGuestbooks = async () => {
            try {
                const response = await axios.get<PaginatedResponse>(`/board/guestbook?page=${page + 1}&size=${rowsPerPage}`);
                if (isMounted) {
                    if (response.data && Array.isArray(response.data.items)) {
                        const formattedRows = response.data.items.map(row => ({
                            ...row,
                            date: formatDate(row.date)
                        }));
                        setRows(formattedRows);
                        setTotalCount(response.data.totalCount);
                    } else {
                        console.error('Received data is not in the expected format');
                    }
                }
            } catch (error) {
                if (isMounted) {
                    console.error('Error fetching guestbooks:', error);
                }
            }
        };

        fetchGuestbooks().then(() => isMounted = false);
    }, [page, rowsPerPage]);

    const handleChangePage = (
        event: React.MouseEvent<HTMLButtonElement> | null,
        newPage: number,
    ) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (
        event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

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
                                        selected={selectedId === row.id}
                                        sx={{
                                            '&:last-child td, &:last-child th': {border: 0},
                                            cursor: row.secret ? 'default' : 'pointer',
                                        }}
                                        onClick={() => boardClick(row)}
                                    >
                                        <TableCell component="th" scope="row">
                                            {row.id}
                                        </TableCell>
                                        <TableCell align="left">
                                            {row.title}
                                            {row.secret && (
                                                <IconButton size="small">
                                                    <Lock fontSize="small"/>
                                                </IconButton>
                                            )}
                                        </TableCell>
                                        <TableCell align="right">{row.writer}</TableCell>
                                        <TableCell align="right">{row.date}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                            <TableFooter>
                                <TableRow>
                                    <TablePagination
                                        rowsPerPageOptions={[5, 10, 25]}
                                        colSpan={4}
                                        count={totalCount}
                                        rowsPerPage={rowsPerPage}
                                        page={page}
                                        slotProps={{
                                            select: {
                                                inputProps: {
                                                    'aria-label': '페이지당 행 수',
                                                },
                                                native: true,
                                            },
                                        }}
                                        onPageChange={handleChangePage}
                                        onRowsPerPageChange={handleChangeRowsPerPage}
                                        ActionsComponent={TablePaginationActions}
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                    </TableContainer>
                </AccordionDetails>
            </Accordion>
            {boardData && (
                <BoardDetail board={boardData} onClose={deselectBoard}/>
            )}
            <Fab
                color="primary"
                aria-label="writing guestboard"
                style={{position: 'fixed', bottom: 30, right: 30}}
                onClick={() => navigate('/guestbook/write')}
            >
                <Create/>
            </Fab>
        </div>
    );
}

export default Guestbook;