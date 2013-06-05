
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_32F;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import org.opencv.core.*;

import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCanny;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGoodFeaturesToTrack;
import static com.googlecode.javacv.cpp.opencv_features2d.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.CanvasFrame;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvPoint2D32f;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

  class head{
	public static void main (String[] args) {
		
	matopn();
	//	capture();
		//harris();
		// IplImage image = cvLoadImage("1.png");
		// int x;
		// canny(1);
		 
		// canny(2);
		// process();
	}
		
		public static void capture(){
			  
			  final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		      int i;
		      for(i=1;i<=12;i++)
		      {
			  try {
		            grabber.start();
		             IplImage img = grabber.grab();
		             if (img != null && i==5)
		             {
		 cvFlip(img, img, 1);
		 cvSaveImage("1.png", img);
		 
		             }
		             grabber.start();
		             IplImage img2 = grabber.grab();
		             if (img2 != null && i==9)
		             {
		 cvFlip(img2, img2, 1);
		 cvSaveImage("2.png", img2);
		 
		             }
		         } catch (Exception e)
		         {
		             e.printStackTrace();
		        }
		      }
								}
		
		public static void process(){
			IplImage img = cvLoadImage("1.png");
	        IplImage img1 = cvLoadImage("2.png");
	        
	       // Imgproc.GaussianBlur(img, img1, new Size(15,15),50);
	        int max_corners = 500;
	        CvSize img_sz = cvGetSize(img);


	        IplImage gray = cvCreateImage(img_sz, IPL_DEPTH_8U, 1);
	        IplImage cpy = img.clone();
	        IplImage gray1 = cvCreateImage(img_sz, IPL_DEPTH_8U, 1);
	        IplImage cpy1 = img.clone();

	        cvCvtColor(img, gray, CV_BGR2GRAY);
	        cvCvtColor(img1, gray1, CV_BGR2GRAY);

	        IplImage eig_img = cvCreateImage(img_sz, IPL_DEPTH_32F, 1);
	        IplImage tmp_img = cvCreateImage(img_sz, IPL_DEPTH_32F, 1);
	        
	        int[] corner_count = { max_corners };
	        CvPoint2D32f corners = new CvPoint2D32f(max_corners);
	        double qltyLevel = 0.05;
	        double minDist = 5.0;
	        CvArr mask = null;
	        int blkSize = 3;
	        int useHarris = 1;
	        double k = 0.04;

	        int[] corner_count1 = { max_corners };
	        CvPoint2D32f corners1 = new CvPoint2D32f(max_corners);
	        double qltyLevel1 = 0.002;
	        double minDist1 = 5.0;
	        CvArr mask1 = null;
	        int blkSize1 = 8;
	        int useHarris1 = 0;
	        double k1 = 0.04;
	        cvGoodFeaturesToTrack(gray, eig_img, tmp_img, corners, corner_count, qltyLevel, minDist, mask, blkSize, useHarris, k);
	        cvGoodFeaturesToTrack(gray1, eig_img, tmp_img, corners1, corner_count1, qltyLevel1, minDist1, mask1, blkSize1, useHarris1, k1);
	    //   cvSiftFeatureDetector(gray1, eig_img, tmp_img, corners1, corner_count1, qltyLevel1, minDist1, mask1, blkSize1, useHarris1, k1);
	        if(corner_count[0]-corner_count1[0]<=20){
	        	System.out.println("same" );
	        }
	        else
	        {
	        	System.out.println("not same" );
	        }
		}
		public static void canny(int x){
			IplImage image = cvLoadImage(x+".png");
			
			//create grayscale IplImage of the same dimensions, 8-bit and 1 channel
	        IplImage imageGray = cvCreateImage(cvSize(image.width(), image.height()), IPL_DEPTH_8U, 1);
	        
	        //convert image to grayscale
	        cvCvtColor(image, imageGray, CV_BGR2GRAY );
	         
	        //create canvas frame named 'Demo'
	  //      CanvasFrame canvas = new CanvasFrame("RGB");
	       CanvasFrame canvas2 = new CanvasFrame("Gray"); 
	        //Show image in canvas frame
	       // canvas.showImage(image);
	      canvas2.showImage(imageGray);
			
	        IplImage gray = new IplImage(imageGray);
	        
	        
	        IplImage edge = cvCreateImage(cvSize(gray.width(), gray.height()), IPL_DEPTH_32F, 1);
	        
	        //run Canny edge detection..
	/* if the edges are not seen change the 3 to 5 or 7.. it shows more edges */
	        cvCanny(gray, edge, 150, 240, 3);
	        new CanvasFrame("Edge").showImage(edge); 
	      cvSaveImage(x+".png",edge);
	        
		}

		public static void harris(){
			IplImage img = cvLoadImage("1.png");
			
		//	Mat img = imread("1.png");
	      //  IplImage img1 = cvLoadImage("2.png");
	        //IplImage img2=  cvLoadImage("2.png");
	        CvSize img_sz = cvGetSize(img);
	       
	        IplImage edge = cvCreateImage(img_sz, IPL_DEPTH_32F, 1);
	        //cvCvtScale(img, harri,1,0);
	      edge= convertw(img);
	  
     //  cvConvertTo(img,img,CV_32F);
	       // cvCornerHarris( img, edge, 128, 255, 0.05);
	    //    cvSaveImage("2.png", img);
	       // Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
		}
		
	public static 	IplImage convertw(IplImage img)
		{
		    IplImage img32f = cvCreateImage(cvGetSize(img),IPL_DEPTH_32F,img.nChannels());
CvScalar z,zz;
		    for(int i=0; i<img.height(); i++)
		    {
		        for(int j=0; j<img.width(); j++)
		        {
		        //-------get and convert values///
		        z=	cvGet2D(img,i,j);
		        double blu =z.blue();
		        blu=blu/255;
		        double gree =z.green();
		        gree=gree/255;
		        double rd=z.red();
		        rd=rd/255;
		        
		        //--convert to floats type cast them////
		        float frd =(float) rd;
		        float fgree =(float) gree;
		        float fblu =(float) blu;
		        //----put the values back in image///
		//this is nt wrking :)
		        z.blue(fblu);
		        z.green(gree);
		        z.red(frd);
		       
		       
		        //cvSet(img, cvScalar(rd,gree,blu,rd));
		        System.out.println( fblu );
		        	cvSet2D(img32f,i,j,z);
		        //cvSaveImage("2.png", img);
		  
		        }
		    }
		    cvSaveImage("32.png", img32f);
		    return img32f;
		}
	
	public static  void matopn()
	{
		IplImage img = cvLoadImage("1.png");
		 IplImage img32f = cvCreateImage(cvGetSize(img),IPL_DEPTH_32F,1);
		 cvCvtColor(img32f, img32f, CV_BGR2GRAY );
		 IplImage img3 = cvCreateImage(cvGetSize(img),IPL_DEPTH_32F,1);
		 cvCvtColor(img3,img3, CV_BGR2GRAY );
		  cvCornerHarris(img32f,img3,3,5,0.5);
		cvConvertScale(img,img32f,1.23,0); 
		CvScalar z;
		 for(int i=0; i<img.height(); i++)
		    {
		        for(int j=0; j<img.width(); j++)
		        {
		        	z=	cvGet2D(img32f,i,j);
		        	System.out.println(z.blue());
		        }
		   
		        }
		
		
		  cvSaveImage("32.png", img32f);
		
	}
  
  }
	
  
  
 