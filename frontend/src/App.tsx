import React from 'react';
import './assets/styles/App.scss';
import ResponsiveAppBar from './component/AppBar';
import Guestbook from './page/Guestbook';
import Main from './page/Main';
import {Route, Routes} from "react-router";

function App() {
    return (
        <div className="App">
            <ResponsiveAppBar />
            <div className={'content'}>
                <Routes>
                    <Route path="/" element={<Main />} />
                    <Route path="/guestbook" element={<Guestbook />} />
                    <Route path="/*" element={<Main />} />
                </Routes>
            </div>
        </div>
    );
}

export default App;
