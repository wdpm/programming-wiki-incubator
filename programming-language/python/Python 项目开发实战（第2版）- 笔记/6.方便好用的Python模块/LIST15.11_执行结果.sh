$ python mapping_model.py
--- api_user_json ---
{
  "user_id": "bp12345",
  "user_nickname": "tokibito"
}
--- api_user_detail_json ---
{
  "user_id": "bp12345",
  "user_nickname": "tokibito",
  "user_age": 26
}
--- api_comment_json ---
{
  "text": "Hello, world!",
  "user": {
    "user_id": "bp12345",
    "user_nickname": "tokibito"
  }
}
