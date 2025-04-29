import React from 'react';
import {Responsive, WidthProvider} from 'react-grid-layout';
import {Card, Paper} from '@mui/material';
import NotionPortlet from '@component/portlet/NotionPortlet';
import GithubContributionPortlet from '@component/portlet/GithubContributionPortlet';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import devGbparkImage from '@image/dev-gbpark.webp';
import devWaiImage from '@image/wai.png';

const ResponsiveGridLayout = WidthProvider(Responsive);

const DraggableGrid = () => {
    const layout = [
        { i: 'notion', x: 0, y: 0, w: 3, h: 3 },
        { i: 'pic', x: 3, y: 0, w: 3, h: 3 },
        { i: 'wai', x: 0, y: 2, w: 2, h: 4 },
        { i: 'gitHub', x: 2, y: 2, w: 4, h: 4 },
    ];

    return (
        <ResponsiveGridLayout
            className='layout'
            layouts={{ lg: layout }}
            breakpoints={{ lg: 1200, md: 996, sm: 768, xs: 480, xxs: 0 }}
            cols={{ lg: 6, md: 6, sm: 2, xs: 1, xxs: 1 }}
            rowHeight={100}
            width={1000}
            isResizable={false}
            isDraggable={false}
            style={{width: '100%'}}
        >
            <Paper key='notion' style={{ padding: 16 }}>
                <NotionPortlet/>
            </Paper>
            <Paper key='pic' style={{ padding: 16}}>
                <Card className={'ff'}>
                        <img
                            src={devGbparkImage}
                            loading='lazy'
                            alt=''
                        />
                </Card>
            </Paper>
            <Paper key='wai' style={{ padding: 16}}>
                <Card className={'ff'}>
                    <img
                        src={devWaiImage}
                        loading='lazy'
                        alt=''
                    />
                </Card>
            </Paper>
            <Paper key='gitHub' className={'flex-center'} style={{ padding: 16, overflow:'hidden'}}>
                <GithubContributionPortlet/>
            </Paper>
        </ResponsiveGridLayout>
    );
};

export default DraggableGrid;
