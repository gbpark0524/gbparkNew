import React, {useState} from 'react';
import './App.css';
import ResponsiveAppBar from './component/AppBar';

function App() {
    const [list] = useState(['남자 코트 추천', '강남우동맛집', '파이썬']);
    const [thumb, setThumb] = useState([0, 0, 0]);
    const [modal, setModal] = useState(false);
    const [modalTitle, setModalTitle] = useState(0);

    return (
        <div className="App">
            <ResponsiveAppBar />
            <div style={{ marginTop: '64px' }}>
                {
                    list.map((item, i) => {
                        return (
                            <div className="list" key={i}>
                                <h4 onClick={() => {
                                    setModal(!modal);
                                    setModalTitle(i);
                                }}>{i}. {item} <span onClick={() => {
                                    const clone = [...thumb];
                                    clone[i]++;
                                    setThumb(clone);
                                }}>따봉</span></h4>{thumb[i]}
                                <p>2월 17일 발행</p>
                            </div>
                        )
                    })
                }

                {
                    modal ? <Modal list={list} modalTitle={modalTitle}/> : null
                }
                {/*<Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/about" element={<About />} />
                     추가할 라우트들 
                </Routes>*/}
            </div>
        </div>
    );

}

interface ModalProps {
    list: string[];
    modalTitle: number;
}

const Modal = ({ list, modalTitle }: ModalProps) => {
    return (
        <div className="modal">
            <h4>{list[modalTitle]}</h4>
            <p>date</p>
            <p>con</p>
        </div>
    )
}

Modal.defaultProps = ['0','0','0'];

export default App;
