import React, {useEffect, useState} from 'react';
import './assets/styles/App.scss';
import ResponsiveAppBar from './component/AppBar';
import Guestbook from './page/Guestbook';
import Main from './page/Main';
import {Route, Routes} from "react-router";
import Loading from "./component/Loading";

function App() {
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setTimeout(() => {
            setIsLoading(false);
        }, 1000); // 3초 후에 로딩 상태를 false로 설정 (테스트용)
    }, []);

    if (isLoading) {
        return <Loading />;
    }

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
