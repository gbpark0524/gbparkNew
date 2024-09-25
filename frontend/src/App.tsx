import React, {useEffect, useState} from 'react';
import '@assets/styles/App.scss';
import ResponsiveAppBar from '@component/AppBar';
import Guestbook from '@page/Guestbook';
import Main from '@page/Main';
import {Route, Routes} from "react-router";
import Loading from "@component/Loading";
import WriteForm from "@component/WriteForm";

function App() {
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setTimeout(() => {
            setIsLoading(false);
        }, 100);
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
                    <Route path="/test" element={<WriteForm />} />
                    <Route path="/*" element={<Main />} />
                </Routes>
            </div>
        </div>
    );
}

export default App;
