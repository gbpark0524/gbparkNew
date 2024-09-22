import styles from '@assets/styles/Board.module.scss';

function BoardDetail() {
    return (
        <div className={styles['content']}>
            <div className={styles['head']}>
                <div className={styles['area-empty']}></div>
                <div className={styles['title']}>Pom.xml 의 scope</div>
                <div className={styles['writer']}>Pom.xml 의 scope</div>
            </div>
            <div className={styles['cont-board']}>
                JPA DB설정중에 인터넷에 검색을 해 보니, h2 DB 설정시 모든 설정을 정상적으로 했음에도 라이버러리를 읽어오지 못한다는 글이 종종 보였다.

                스터디 전엔 어차피 발표도 해야 하니, 그냥 이미 구입한 톰캣 사이트(cafe24) maria DB에 연결했었고, 그 땐 별다른 이슈가 없었다. 하지만, 스터디에 가서 사람들이 h2 설정에 트러블이 많았다는 말에 h2도 공부할 겸 설정을 해 보기로 하였다.
            </div>
        </div>
    );
}

export default BoardDetail;
