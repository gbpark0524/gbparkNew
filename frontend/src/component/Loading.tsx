import React from 'react';
import {Box, useTheme} from '@mui/material';
import styles from '../assets/styles/Loading.module.scss'
import Typography from "@mui/material/Typography";

const Loading = ({fullScreen = true}) => {
    const theme = useTheme();

    const content = (
        <div className={styles.loading}>
            <div className={styles.boxes}>
                <div className={styles.box}>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div className={styles.box}>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div className={styles.box}>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <div className={styles.box}>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
                <Typography
                    variant="h4"
                    style={{marginTop: theme.spacing(12)}}
                    color="textSecondary"
                >
                    Loading
                </Typography>
            </div>

        </div>
    );

    return fullScreen ? (
        <Box
            position="fixed"
            top={0}
            left={0}
            right={0}
            bottom={0}
            zIndex={theme.zIndex.modal + 1}
        >
            {content}
        </Box>
    ) : content;
};

export default Loading;