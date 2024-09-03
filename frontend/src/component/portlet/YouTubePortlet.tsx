import React from 'react';

const YouTubeMusicPlaylist = () => {
    const playlistId = 'PLbkbDd3Tye668O935H5Zv6zym74VR3K3u';
    const embedUrl = `https://www.youtube.com/embed/videoseries?list=${playlistId}`;

    return (
        <div className={['ff', 'flex-center'].join(' ')}>
            <div className={'ratio16x9'}>
                <iframe
                    title={'youtube-music'}
                    className={'ff'}
                    src={embedUrl}
                    allow="autoplay; encrypted-media"
                    allowFullScreen
                />
            </div>
        </div>
    );
};

export default YouTubeMusicPlaylist;