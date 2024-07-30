import React, {useState} from 'react';
import './App.css';

function App() {
    const [a, setA] = useState(['남자 코트 추천', '강남우동맛집', '파이썬']);
    const [thumb, setThumb] = useState(0);

    return (
        <div className="App">
            <div className="black-nav">
            </div>
            <h4>블로그임</h4>
            <button onClick={() => {
                const copy = [...a];
                copy.sort();
                setA(copy)
            }}>ㅅㅅ
            </button>
            <div className="list">
                <h4>{a[0]} <span onClick={() => {
                    setThumb(thumb + 1)
                }}>따봉</span></h4>{thumb}
                <p>2월 17일 발행</p>
            </div>
            <div className="list">
                <h4>{a[1]} </h4>
                <p>2월 17일 발행</p>
            </div>
            <div className="list">
                <h4>{a[2]}</h4>
                <p>2월 17일 발행</p>
            </div>
        </div>
    );

}

export default App;
