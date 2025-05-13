/**
 * 이미지 경로 처리 유틸리티
 * thumbnailUrl이 http로 시작하면 그대로 사용하고,
 * 그렇지 않으면 로컬 assets/image 폴더의 이미지를 사용합니다.
 */

// React 애플리케이션에서 이미지 import 시 고려해야 할 사항들
// 1. 동적 import는 require.context 또는 특정 번들러 기능이 필요
// 2. CRA, Webpack, Vite 등 환경에 따라 구현이 다를 수 있음

// 동적으로 이미지를 로드하기 위한 이미지 매핑
// 이 매핑을 통해 로컬 이미지를 참조할 수 있음
const imageMap: Record<string, string> = {
  // 이미지 파일명: 이미지 모듈 맵핑
  'default-project-image.png': require('@image/default-project-image.png'),
  'cookie-converter.png': require('@image/cookie-converter.png'),
  'dev-gbpark.webp': require('@image/dev-gbpark.webp'),
  'gbpark-new.png': require('@image/gbpark-new.png'),
  'wai.png': require('@image/wai.png'),
  // 필요에 따라 여기에 더 많은 이미지 추가 가능
};

/**
 * 이미지 경로를 처리하는 함수
 * @param imagePath 이미지 경로 문자열
 * @returns 처리된 이미지 경로
 */
export const resolveImagePath = (imagePath: string | undefined): string => {
  // imagePath가 비어있으면 빈 문자열 반환
  if (!imagePath) {
    return '';
  }

  // http나 https로 시작하면 외부 URL이므로 그대로 사용
  if (imagePath.startsWith('http://') || imagePath.startsWith('https://')) {
    return imagePath;
  }

  // 파일명만 있는 경우 (경로 없이)
  const fileName = imagePath.split('/').pop();
  
  if (fileName && fileName in imageMap) {
    return imageMap[fileName];
  }

  // 경로에 파일명이 포함된 경우
  for (const key in imageMap) {
    if (imagePath.includes(key)) {
      return imageMap[key];
    }
  }

  // 이미지를 찾을 수 없는 경우 콘솔에 에러 로그 출력
  console.error(`이미지를 찾을 수 없습니다: ${imagePath}`);
  return '';
};
