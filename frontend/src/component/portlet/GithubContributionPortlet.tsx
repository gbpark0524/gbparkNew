import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from '../../assets/styles/GithubContributionPortlet.module.scss'

interface GithubResponse {
    date: string;
    commitCount: number;
    color: string;
}

interface Cell {
    commits: number;
    color: string;
}

const GithubContributionPortlet: React.FC = () => {
    const [cells, setCells] = useState<Cell[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get<GithubResponse[]>('/portlet/github/contributions');
                const recentData = response.data.slice(-16); // 최근 16일의 데이터
                const cellData: Cell[] = recentData.map(day => ({
                    commits: day.commitCount,
                    color: day.color
                }));
                setCells(cellData);
            } catch (error) {
                console.error('Error fetching GitHub data:', error);
                // 에러 시 빈 셀로 채움
                setCells(Array(16).fill({ commits: 0, delay: 0, color: '#ebedf0' }));
            }
        };

        fetchData();
    }, []);

    return (
        <div className={styles['content']}>
            <div className={styles['plate-grid']}>
                <div className={styles['area-grid']}>
                    {cells.map((cell, index) => (
                        <div
                            key={index}
                            data-commits={cell.commits}
                            className={styles['cell-' + index]}
                            style={{
                                backgroundColor: cell.color,
                            }}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default GithubContributionPortlet;