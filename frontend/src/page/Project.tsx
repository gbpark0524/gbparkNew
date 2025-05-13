import React, { useCallback, useEffect, useRef, useState } from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    Grid,
    Pagination,
    Typography,
    Tooltip,
} from '@mui/material';
import { ArrowDropDown } from '@mui/icons-material';
import axios from "axios";
import styles from '@assets/styles/Project.module.scss';
import defaultProjectImg from '@image/default-project-image.png';
import { resolveImagePath } from '@utils/imageUtils';

interface PaginatedResponse {
    page: number;
    size: number;
    totalCount: number;
    totalPage: number;
    items: ProjectData[];
}

// 백엔드에서 반환하는 프로젝트 데이터 구조
interface ProjectData {
    id: number;
    title: string;
    content: string;
    projectUrl: string;
    thumbnailUrl: string;
    tags: string[];
    createdDate: string;
    lastModifiedDate: string;
    secret: boolean;
}

const Project = (): React.ReactElement => {
    const [projects, setProjects] = useState<ProjectData[]>([]);
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const isMountedRef = useRef(false);

    // 프로젝트 데이터 호출
    const fetchProjects = useCallback(async () => {
        if (isMountedRef.current) return;
        isMountedRef.current = true;
        try {
            const response = await axios.get<PaginatedResponse>(
                `/board/project?page=${page}&size=6`
            );

            // 서버 응답 데이터 확인을 위한 로깅
            console.log('서버 응답 데이터:', response.data);

            if (response.data && Array.isArray(response.data.items)) {
                setProjects(response.data.items);
                setTotalPages(response.data.totalPage || 1);
            } else {
                console.error('프로젝트 데이터 형식이 예상과 다릅니다:', response.data);

                // 응답 형식이 예상과 다를 경우를 위한 대비책
                // 직접 배열인 경우 처리
                if (Array.isArray(response.data)) {
                    setProjects(response.data);
                    setTotalPages(1); // 기본값
                }
            }
        } catch (error) {
            console.error('프로젝트 데이터 로딩 실패:', error);
        }
    }, [page]);

    // 프로젝트 클릭 핸들러
    const handleProjectClick = (id: number) => {
        // 클릭한 프로젝트 찾기
        const project = projects.find(p => p.id === id);
        if (project && project.projectUrl) {
            window.open(project.projectUrl, '_blank', 'noopener,noreferrer');
        }
        console.log(`프로젝트 ${id} 클릭됨`);
    };

    // 페이지 변경 핸들러
    const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    // 초기 데이터 로드
    useEffect(() => {
        fetchProjects().then(() => isMountedRef.current = false);
    }, [fetchProjects]);

    return (
        <div className={styles.projectContainer}>
            <Accordion defaultExpanded>
                <AccordionSummary
                    expandIcon={<ArrowDropDown />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    <Typography variant={'h4'}>프로젝트</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Grid container spacing={3}>
                        {projects.map((project) => (
                            <Grid item xs={12} sm={6} md={4} key={project.id}>
                                <Tooltip
                                    title={project.content}
                                    arrow
                                    placement="bottom"
                                    enterDelay={500}
                                    leaveDelay={200}
                                >
                                    <Card className={styles.projectCard}>
                                        <CardActionArea onClick={() => handleProjectClick(project.id)}>
                                            <CardMedia
                                                component="img"
                                                height="180"
                                                image={project.thumbnailUrl ? resolveImagePath(project.thumbnailUrl) : defaultProjectImg}
                                                alt={project.title}
                                                className={styles.projectImage}
                                            />
                                            <CardContent className={styles.projectContent}>
                                                <Typography gutterBottom variant="h6" component="div" className={styles.projectTitle}>
                                                    {project.title}
                                                </Typography>
                                            </CardContent>
                                        </CardActionArea>
                                    </Card>
                                </Tooltip>
                            </Grid>
                        ))}
                    </Grid>

                    {totalPages > 1 && (
                        <div className={styles.pagination}>
                            <Pagination
                                count={totalPages}
                                page={page}
                                onChange={handlePageChange}
                                color="primary"
                            />
                        </div>
                    )}
                </AccordionDetails>
            </Accordion>
        </div>
    );
}

export default Project;