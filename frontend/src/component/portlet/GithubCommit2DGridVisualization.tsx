import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface GithubResponse {
    date: string;
    commitCount: number;
    color: string;
}

interface Cell {
    commits: number;
    delay: number;
    color: string;
}

const GithubCommit4x4GridAnimation: React.FC = () => {
    const [cells, setCells] = useState<Cell[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get<GithubResponse[]>('/portlet/github/contributions');
                const recentData = response.data.slice(-16); // 최근 16일의 데이터
                const cellData: Cell[] = recentData.map(day => ({
                    commits: day.commitCount,
                    delay: Math.random() * 2, // Random delay between 0 and 2 seconds
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
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '75%', width: '75%' }}>
            <div style={{
                position: 'relative',
                width: '400px',
                height: '400px',
                transform: 'rotateX(60deg) rotateZ(-45deg)',
                transformStyle: 'preserve-3d',
            }}>
                <div
                    style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(4, 1fr)',
                        gap: '4px',
                        width: '100%',
                        height: '100%',
                        backgroundColor: 'white',
                        padding: '10px',
                        borderRadius: '8px',
                        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
                    }}
                >
                    {cells.map((cell, index) => (
                        <div
                            key={index}
                            data-commits={cell.commits}
                            style={{
                                backgroundColor: cell.color,
                                borderRadius: '4px',
                                animation: cell.commits > 0 ? `fallDown 1s ease-in-out ${cell.delay}s forwards` : 'none',
                                opacity: cell.commits > 0 ? 0 : 1,
                            }}
                        />
                    ))}
                </div>
            </div>
            <style>
                {`
                @keyframes fallDown {
                    0% {
                        transform: translateY(-200%) translateX(100%) translateZ(300px);
                        opacity: 0;
                    }
                    60% {
                        transform: translateY(15%) translateX(-10%) translateZ(-40px);
                        opacity: 1;
                    }
                    80% {
                        transform: translateY(-5%) translateX(5%) translateZ(20px);
                    }
                    100% {
                        transform: translateY(0) translateX(0) translateZ(0);
                        opacity: 1;
                    }
                }
                `}
            </style>
        </div>
    );
};

export default GithubCommit4x4GridAnimation;