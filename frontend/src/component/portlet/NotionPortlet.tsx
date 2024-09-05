import React, {useEffect, useState} from 'react';
import {
    List,
    ListItem,
    ListItemText,
    Typography,
    Link
} from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import axios, {AxiosResponse} from "axios";

interface NotionPage {
    id: string;
    title: string;
    url: string;
    iconType: string;
    iconContent: string;
}

interface Response {
    success: boolean;
    message: string;
    data : NotionPage[];
}

const NotionPortlet = () => {
    const [pages, setPages] = useState<NotionPage[]>([]);
    
    useEffect(() => {
        axios.get<Response>('/portlet/notion/list/10')
            .then((response: AxiosResponse<Response>) => {
                if (response.data.success && Array.isArray(response.data.data)) {
                    setPages(response.data.data);
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
                    <ListItem key={page.id} divider={index !== pages.length - 1}>
                        <ListItemText
                            primary={
                                <Link
                                    href={page.url}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    underline="hover"
                                    color="inherit"
                                >
                                    {page.iconContent && (
                                        <span style={{ marginRight: '8px' }}>
                                            {page.iconType === 'EMOJI' ? page.iconContent : '🔗'}
                                        </span>
                                    )}
                                    {page.title}
                                </Link>
                            }
                        />
                    </ListItem>
                ))}
            </List>
        </div>
    );
};

export default NotionPortlet;