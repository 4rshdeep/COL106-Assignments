* on input.txt consisting of 20 words average time take in around 230ms when I used scanner to read the file
* Using Buffered Reader reduced the speed to 180ms (output files in tests/out1.txt)
* BufferedOutputStram speed 166ms (Time reduces a bit) 
* Using a Printwriter in top of BufferedOutPutStream Reduces it further to 140 ms
* After Using BufferReader and Print Writer achieved time of 43 seconds on 25000 words computing 9505273 anagrams in total
