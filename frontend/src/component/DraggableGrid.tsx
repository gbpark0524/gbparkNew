import React from 'react';
import {Responsive, WidthProvider} from 'react-grid-layout';
import {Paper} from '@mui/material';
import GitHubPortlet from "@component/portlet/GitHubPortlet";
import NotionPortlet from "@component/portlet/NotionPortlet";
import GithubContributionPortlet from "@component/portlet/GithubContributionPortlet";
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';

const ResponsiveGridLayout = WidthProvider(Responsive);

const DraggableGrid = () => {
    const layout = [
        { i: 'a', x: 0, y: 0, w: 2, h: 3 },
        { i: 'gitHub', x: 0, y: 2, w: 7, h: 4 },
        { i: 'c', x: 2, y: 0, w: 2, h: 3 }
    ];

    return (
        <ResponsiveGridLayout
            className="layout"
            layouts={{ lg: layout }}
            breakpoints={{ lg: 1200, md: 996, sm: 768, xs: 480, xxs: 0 }}
            cols={{ lg: 6, md: 4, sm: 2, xs: 1, xxs: 1 }}
            rowHeight={100}
            width={1000}
            isResizable={false}
            isDraggable={false}
            style={{width: '100%'}}
        >
            <Paper key="a" style={{ padding: 16 }}>
                <NotionPortlet/>
            </Paper>
            <Paper key="gitHub" className={'flex-center'} style={{ padding: 16, overflow:'hidden'}}>
                <GithubContributionPortlet/>
            </Paper>
            <Paper key="c" style={{ padding: 16}}>
                <GitHubPortlet/>
            </Paper>
        </ResponsiveGridLayout>
    );
};

export default DraggableGrid;
