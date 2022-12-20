-- 参数：用户id
local userId = ARGV[1]
-- 参数：剩余容量key
local numKey = KEYS[1]
-- 参数：已报名用户列表key
local userListKey = KEYS[2]
-- 获取剩余容量
local num = redis.call('GET', numKey)
-- 剩余容量不足：0
if(tonumber(num) <= 0 ) then return "0"
end
-- 重复报名
local alreadySignUp = redis.call("SISMEMBER", userListKey, userId)
if(alreadySignUp == 1) then return "1"
end
-- 剩余容量减1
redis.call("DECR", numKey)
-- 用户id加入报名成功集合
redis.call("SADD", userListKey, userId)
-- 报名成功
return "2"