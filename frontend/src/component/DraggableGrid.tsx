import React from 'react';
import { Responsive, WidthProvider } from 'react-grid-layout';
import { Paper, Typography } from '@mui/material';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';

const ResponsiveGridLayout = WidthProvider(Responsive);

const DraggableGrid = () => {
    const layout = [
        { i: 'a', x: 0, y: 0, w: 2, h: 7 },
        { i: 'b', x: 2, y: 0, w: 2, h: 4 },
        { i: 'c', x: 4, y: 0, w: 2, h: 3 }
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
            isDraggable={true}
            style={{width: '100%'}}
        >
            <Paper key="a" style={{ padding: 16 }}>
                <Typography variant="h6">항목 A</Typography>
            </Paper>
            <Paper key="b" style={{ padding: 16 }}>
                <Typography variant="h6">항목 B</Typography>
            </Paper>
            <Paper key="c" style={{ padding: 16 }}>
                <Typography variant="h6">항목 C</Typography>
            </Paper>
        </ResponsiveGridLayout>
    );
};

export default DraggableGrid;