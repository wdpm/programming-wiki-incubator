package main

import (
	"log"

	"github.com/narenaryan/encryptString/utils"
)

// AES keys should be of length 16, 24, 32
func main() {
	key := "111023043350789514532147"
	message := "I am A Message"
	log.Println("Original message: ", message)
	encryptedString := utils.EncryptString(key, message)
	log.Println("Encrypted message: ", encryptedString)
	decryptedString := utils.DecryptString(key, encryptedString)
	log.Println("Decrypted message: ", decryptedString)
}

// 2023/03/12 13:13:03 Original message:  I am A Message
// 2023/03/12 13:13:03 Encrypted message:  8/+JCfTb+ibIjzQtmCo=
// 2023/03/12 13:13:03 Decrypted message:  I am A Message
