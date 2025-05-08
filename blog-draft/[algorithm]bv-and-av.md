# BV 号和 AV 号的转化

来源：https://github.com/SocialSisterYi/bilibili-API-collect/issues/847#issuecomment-1807020675
```python
XOR_CODE = 23442827791579
MASK_CODE = 2251799813685247
MAX_AID = 1 << 51;

data = [b'F', b'c', b'w', b'A', b'P', b'N', b'K', b'T', b'M', b'u', b'g', b'3', b'G', b'V', b'5', b'L', b'j', b'7', b'E', b'J', b'n', b'H', b'p', b'W', b's', b'x', b'4', b't', b'b', b'8', b'h', b'a', b'Y', b'e', b'v', b'i', b'q', b'B', b'z', b'6', b'r', b'k', b'C', b'y', b'1', b'2', b'm', b'U', b'S', b'D', b'Q', b'X', b'9', b'R', b'd', b'o', b'Z', b'f']

BASE = 58
BV_LEN = 12
PREFIX = "BV1"

def av2bv(aid):
    bytes = [b'B', b'V', b'1', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0']
    bv_idx = BV_LEN - 1
    tmp = (MAX_AID | aid) ^ XOR_CODE
    while int(tmp) != 0:
        bytes[bv_idx] = data[int(tmp % BASE)]
        tmp /= BASE
        bv_idx -= 1
    bytes[3], bytes[9] = bytes[9], bytes[3]
    bytes[4], bytes[7] = bytes[7], bytes[4]
    return "".join([i.decode() for i in bytes])

def bv2av(bvid: str):
    bvid = list(bvid)
    bvid[3], bvid[9] = bvid[9], bvid[3]
    bvid[4], bvid[7] = bvid[7], bvid[4]
    bvid = bvid[3:]
    tmp = 0
    for i in bvid:
        idx = data.index(i.encode())
        tmp = tmp * BASE + idx
    return (tmp & MASK_CODE) ^ XOR_CODE

print(av2bv(111298867365120))
print(bv2av("BV1L9Uoa9EUx"))
```