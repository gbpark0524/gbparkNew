import React from 'react';
import ReactDOM from 'react-dom/client';
import App from '@/App';
import reportWebVitals from '@/reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import {ThemeProvider, CssBaseline} from "@mui/material";
import {Provider} from "react-redux";
import store from "store";
import theme from "@assets/theme";
import '@styles/globals.scss';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <CssBaseline></CssBaseline>
            <ThemeProvider theme={theme}>
            <BrowserRouter>
                <App/>
            </BrowserRouter>
            </ThemeProvider>
        </Provider>
    </React.StrictMode>
);

reportWebVitals();
