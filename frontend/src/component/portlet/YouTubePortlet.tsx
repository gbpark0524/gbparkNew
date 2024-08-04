import React from 'react';
import '../../assets/styles/YouTubePortlet.scss';

const YouTubeMusicPlaylist: React.FC = () => {
    const playlistId = 'PLbkbDd3Tye668O935H5Zv6zym74VR3K3u';
    const embedUrl = `https://www.youtube.com/embed/videoseries?list=${playlistId}`;

    return (
        <div className={['ff', 'flex-center'].join(' ')}>
            <div className={'responsiveWrapper'}>
                <iframe
                    title={'youtube-music'}
                    className={'responsiveIframe'}
                    src={embedUrl}
                    allow="autoplay; encrypted-media"
                    allowFullScreen
                />
            </div>
        </div>
    );
};

export default YouTubeMusicPlaylist;