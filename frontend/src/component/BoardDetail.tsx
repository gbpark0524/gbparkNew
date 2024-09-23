import styles from '@assets/styles/Board.module.scss';

interface BoardDetailProps {
    board : {
        title: string,
        writer: string,
        content: string,
    }
}
const BoardDetail = ({board} : BoardDetailProps) => {
    return (
        <div className={styles['content']}>
            <div className={styles['head']}>
                <div className={styles['area-empty']}></div>
                <div className={styles['title']}>{board.title}</div>
                <div className={styles['writer']}>{board.writer}</div>
            </div>
            <div className={styles['cont-board']}>
                {board.content}
            </div>
        </div>
    );
}

export default BoardDetail;
