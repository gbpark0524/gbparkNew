import React from 'react';
import {CardContent, CardActions, Typography, Button, Avatar, Box, Link} from '@mui/material';
import GitHubIcon from '@mui/icons-material/GitHub';

const GitHubPortlet = () => {
    // 이 정보는 실제로는 GitHub API를 통해 동적으로 가져와야 합니다
    const githubInfo = {
        username: "gbpark0524",
        avatarUrl: "https://github.com/gbpark0524.png",
        profileUrl: "https://github.com/gbpark0524",
        commitActivity: [
            [0, 1, 2, 3, 4, 0, 0],
            [0, 0, 1, 2, 3, 4, 0],
            [1, 0, 0, 1, 2, 3, 4],
            [2, 1, 0, 0, 1, 2, 3],
            [3, 2, 1, 0, 0, 1, 2],
            [4, 3, 2, 1, 0, 0, 1],
            [0, 4, 3, 2, 1, 0, 0],
        ],
        recentCommits: [
            {
                hash: "6d1f529",
                repo: "gbparkNew",
                url: "https://github.com/gbpark0524/gbparkNew/commit/6d1f529e901ff89346d314c22361f3f7e0752757"
            },
        ]
    };

    const getCommitColor = (count: number) => {
        if (count === 0) return '#ebedf0';
        if (count <= 1) return '#9be9a8';
        if (count <= 2) return '#40c463';
        if (count <= 3) return '#30a14e';
        return '#216e39';
    };

    return (
        <div>
            <CardContent>
                <Box sx={{display: 'flex', alignItems: 'center', mb: 2}}>
                    <Avatar
                        src={githubInfo.avatarUrl}
                        sx={{width: 60, height: 60, mr: 2}}
                    />
                    <Typography variant="h6">{githubInfo.username}</Typography>
                </Box>

                <Typography variant="subtitle1" gutterBottom>Commit Activity</Typography>
                <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 1, mb: 2}}>
                    {githubInfo.commitActivity.map((week, weekIndex) => (
                        week.map((day, dayIndex) => (
                            <Box
                                key={`${weekIndex}-${dayIndex}`}
                                sx={{
                                    width: 12,
                                    height: 12,
                                    backgroundColor: getCommitColor(day),
                                    borderRadius: 1
                                }}
                            />
                        ))
                    ))}
                </Box>
            </CardContent>

            <CardActions>
                <Button
                    startIcon={<GitHubIcon/>}
                    variant="contained"
                    fullWidth
                    component={Link}
                    href={githubInfo.profileUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    View on GitHub
                </Button>
            </CardActions>
        </div>
    );
};

export default GitHubPortlet;