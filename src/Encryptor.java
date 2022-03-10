public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int numCells = 0;
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                if (numCells < str.length())
                {
                    letterBlock[i][j] = str.substring(numCells, numCells + 1);
                    numCells++;
                }
                else
                {
                    letterBlock[i][j] = "A";
                }
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String encryption = "";
        for (int i = 0; i < numCols; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                encryption += letterBlock[j][i];
            }
        }
        return encryption;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        int numCells = 0;
        int blockSize = numRows * numCols;
        int numBlocks = message.length() / blockSize;

        if (message.length() % blockSize > 0)
        {
            numBlocks++;
        }

        String encryption = "";

        for (int i = 0; i < numBlocks; i++)
        {
            if (message.length() - numCells < blockSize)
            {
                fillBlock(message.substring((i * blockSize), (i * blockSize) + blockSize));
                numCells += blockSize;
            }
            else
            {
                fillBlock(message.substring((i * blockSize), (i * blockSize) + (message.length() - (i * blockSize))));
            }

            encryption += encryptBlock();
        }

        return encryption;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        int numCells = 0;
        int blockSize = numRows * numCols;
        int numBlocks = encryptedMessage.length() / blockSize;

        if (encryptedMessage.length() % blockSize > 0)
        {
            numBlocks++;
        }

        String decryption = "";

        for (int i = 0; i < numBlocks; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                for (int k = j; k < blockSize; k = k + numRows)
                {
                    decryption += encryptedMessage.substring((i * blockSize) + k, (i * blockSize) + k + 1);
                }
            }
        }

        if (decryption.substring((numBlocks * blockSize) - blockSize).indexOf("A") != -1)
        {
            decryption = decryption.substring(0, ((numBlocks * blockSize) - blockSize) + decryption.substring((numBlocks * blockSize) - blockSize).indexOf("A"));
        }

        return decryption;
    }
}