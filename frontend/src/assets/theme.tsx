import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        mode: 'light',
        primary: {
            light: '#4171be',
            main: '#274472',
            dark: '#0d1726',
        },
        secondary: {
            light: '#9db7d0',
            main: '#5885AF',
            dark: '#34516d',
        },
    },
    typography: {
    },
});

export default theme;