import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import {ThemeProvider, CssBaseline} from "@mui/material";
import theme from "./assets/theme";
import './assets/styles/globals.scss';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <CssBaseline></CssBaseline>
        <ThemeProvider theme={theme}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
        </ThemeProvider>
    </React.StrictMode>
);

reportWebVitals();
