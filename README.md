# Huffman-coding
Description:
File compressing and decompressing using Huffman’s algorithm.
First, the user is asked to choose “1” for compression and “2” for decompression.
The project consists of four classes:
1. Comparator class: comparator class helps to compare the node on the basis of one of its attribute.
2. HuffmanNode class: To Build the minheap (HuffmanTree).
3. Encode class: For file compression Implemented Methods:
• ReadFile: Reads Input file in bytes.
• Encode: Generates Huffman tree.
• PrintHuffmanTree: Generat the “newCode” for compression.
• PrintCompressedFile: write the compressed file “a.txt”
First it prints the Header File in the following format:
      Code
1101011 00
1101111 010 1110010 0110 1100110 0111
Then ,it collect every 8bits from the compressed string and convert them to a byte.
NewCode
4.Decode class: for file Decompression. Implemented Methods:
• ReadCompressedFile: Reads the compressed file’s header and the compressed string and convert it to a binary String.
• DecodeFile: Writes the decompressed file using the “codeHashmap”and the binary compressed string.
Data Structures Used:
HashMaps: codeMap and freqMap. Pariority queue: for HaffmanTree.
Algorithm Used : Huffman’s Algorithm
Huffman coding is a lossless data compression algorithm. The idea is to assign variable-length codes to input characters, lengths of the assigned codes are based on the frequencies of corresponding characters. The most frequent character gets the smallest code and the least frequent character gets the largest code.
   
Steps to build Huffman Tree :
1. Create a leaf node for each unique character and build a min heap of all leaf nodes (Min Heap is used as a priority queue. The value of frequency field is used to compare two nodes in min heap. Initially, the least frequent character is at root)
2. Extract two nodes with the minimum frequency from the min heap.
3. Create a new internal node with frequency equal to the sum of the two nodes frequencies. Make the first extracted node as its left child and the other extracted node as its right child. Add this node to the min heap.
4. Repeat steps#2 and #3 until the heap contains only one node. The remaining node is the root node and the tree is complete.
Time complexity: O(nlogn) where n is the number of unique characters. If there are n nodes, extractMin() is called 2*(n – 1) times. extractMin() takes O(logn) time as it calles minHeapify(). So, overall complexity is O(nlogn).
