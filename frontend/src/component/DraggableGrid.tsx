import React from 'react';
import {Responsive, WidthProvider} from 'react-grid-layout';
import {Paper} from '@mui/material';
import GitHubPortlet from "./portlet/GitHubPortlet";
import NotionPortlet from "./portlet/NotionPortlet";
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import GithubContributionPortlet from "./portlet/GithubContributionPortlet";

const ResponsiveGridLayout = WidthProvider(Responsive);

const DraggableGrid = () => {
    const layout = [
        { i: 'a', x: 0, y: 0, w: 2, h: 7 },
        { i: 'b', x: 2, y: 0, w: 2, h: 4 },
        { i: 'c', x: 4, y: 0, w: 2, h: 3 }
    ];

    const pages = [
        { title: '프로젝트 기획안', date: '8월 3일', url: 'https://notion.so/page1' },
        { title: '회의록', date: '8월 1일', url: 'https://notion.so/page2' },
        { title: '디자인 가이드라인', date: '7월 30일', url: 'https://notion.so/page3' },
        { title: '주간 리포트', date: '7월 28일', url: 'https://notion.so/page4' },
        { title: '마케팅 전략', date: '7월 26일', url: 'https://notion.so/page5' }
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
                <NotionPortlet pages={pages}/>
            </Paper>
            <Paper key="b" style={{ padding: 16, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <GithubContributionPortlet/>
            </Paper>
            <Paper key="c" style={{ padding: 16 }}>
                <GitHubPortlet/>
            </Paper>
        </ResponsiveGridLayout>
    );
};

export default DraggableGrid;
