import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '@assets/styles/GithubContributionPortlet.module.scss'
import Tooltip from "@mui/material/Tooltip";
import {Avatar, Box, Button, CardActions, CardContent, Link, Typography} from "@mui/material";
import GitHubIcon from "@mui/icons-material/GitHub";

interface GithubResponse {
    date: string;
    commitCount: number;
    color: string;
}

interface Cell {
    commits: number;
    color: string;
    date: string;
}

const githubInfo = {
    username: "gbpark0524",
    avatarUrl: "https://github.com/gbpark0524.png",
    profileUrl: "https://github.com/gbpark0524",
}

const GithubContributionPortlet = () => {
    const [cells, setCells] = useState<Cell[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get<GithubResponse[]>('/portlet/github/contributions');
                const recentData = response.data.slice(-54); // 최근 16일의 데이터
                const cellData: Cell[] = recentData.map(day => ({
                    commits: day.commitCount,
                    color: day.color,
                    date: day.date,
                }));
                setCells(cellData);
            } catch (error) {
                console.error('Error fetching GitHub data:', error);
                // 에러 시 빈 셀로 채움
                setCells(Array(16).fill({commits: 0, delay: 0, color: '#ebedf0'}));
            }
        };

        fetchData();
    }, []);

    return (
        <div className={styles['content']}>
            <div className={styles['plate-grid']}>
                <div className={styles['area-grid']}>
                    {cells.map((cell, index) => (
                        <Tooltip title={cell.commits.toString() + ' contributions on ' + cell.date} key={index}
                                 slotProps={{
                                     popper: {
                                         modifiers: [
                                             {
                                                 name: 'offset',
                                                 options: {
                                                     offset: [0, -50],
                                                 },
                                             },
                                         ],
                                     },
                                 }}>
                            <div
                                key={index}
                                data-commits={cell.commits}
                                className={styles['cell-' + index]}
                                style={{
                                    backgroundColor: cell.color,
                                }}
                            />
                        </Tooltip>
                    ))}
                </div>
            </div>
            <CardContent style={{height:'100%'}}>
                <CardActions style={{height:'100%'}}>
                    <Button
                        style={{width:'40px', height:'100%'}}
                        variant="outlined"
                        fullWidth
                        component={Link}
                        href={githubInfo.profileUrl}
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <GitHubIcon></GitHubIcon>
                    </Button>
                </CardActions>
            </CardContent>
        </div>
    );
};

export default GithubContributionPortlet;