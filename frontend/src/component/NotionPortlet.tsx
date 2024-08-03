import React from 'react';
import {
    List,
    ListItem,
    ListItemText,
    Typography,
    Link
} from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';

interface Page {
    title: string;
    date: string;
    url: string;  // Notion 페이지 URL
}

interface NotionPortletProps {
    pages: Page[];
}

const NotionPortlet: React.FC<NotionPortletProps> = ({pages}) => {
    return (
        <div>
            <Typography
                variant="h6"
                sx={{
                    p: 2,
                    display: 'flex',
                    alignItems: 'center',
                    gap: 1
                }}
            >
                <DescriptionIcon/>
                Notion
            </Typography>
            <List>
                {pages.map((page, index) => (
                    <ListItem key={index} divider={index !== pages.length - 1}>
                        <ListItemText
                            primary={
                                <Link
                                    href={page.url}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    underline="hover"
                                    color="inherit"
                                >
                                    {page.title}
                                </Link>
                            }
                            secondary={page.date}
                        />
                    </ListItem>
                ))}
            </List>
        </div>
    );
};

export default NotionPortlet;