import React, { useState, useEffect } from 'react';

const GithubCommit3x3GridAnimation = () => {
    const [cells, setCells] = useState<Array<{ commits: number, delay: number }>>([]);

    const generateMockData = () => {
        return Array.from({ length: 9 }, () => ({
            commits: Math.floor(Math.random() * 5),
            delay: Math.random() * 2 // Random delay between 0 and 2 seconds
        }));
    };

    useEffect(() => {
        setCells(generateMockData());
    }, []);

    const getColor = (commits: number): string => {
        if (commits === 0) return '#ebedf0';
        if (commits === 1) return '#9be9a8';
        if (commits === 2) return '#40c463';
        if (commits === 3) return '#30a14e';
        return '#216e39';
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '75%', width: '75%' }}>
            <div style={{
                position: 'relative',
                width: '300px',
                height: '300px',
                transform: 'rotateX(60deg) rotateZ(-45deg)',
                transformStyle: 'preserve-3d',
            }}>
                <div
                    style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(3, 1fr)',
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
                            style={{
                                backgroundColor: getColor(cell.commits),
                                borderRadius: '4px',
                                animation: cell.commits > 0 ? `fallDown 1s ease-in-out ${cell.delay}s` : 'none',
                                opacity: cell.commits > 0 ? 0 : 1,
                                animationFillMode: 'forwards',
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

export default GithubCommit3x3GridAnimation;