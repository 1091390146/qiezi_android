package com.example.qiezi.db.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.qiezi.bean.find.TagInfo;
import com.example.qiezi.bean.find.UserInfo;

import java.util.ArrayList;




/**
 * Created by mac on 16/1/23.
 */

public class UserSp extends BaseSp<UserInfo> {

    public UserSp(Context context) {
        super(context, "user_sp");
    }

    @Override
    public void read(UserInfo user) {
        // 安全检查
        if (user == null) {
            user = new UserInfo();
        }
        if (getSP().contains("userId")) {
            user.setUserId(getSP().getString("userId", ""));
        }
        if (getSP().contains("userIdTemp")) {
            user.setUserIdTemp(getSP().getString("userIdTemp", ""));
        }
        if (getSP().contains("userPhone")) {
            user.setUserPhone(getSP().getString("userPhone", ""));
        }
        if (getSP().contains("userCode")) {
            user.setUserCode(getSP().getString("userCode", ""));
        }
        if (getSP().contains("userName")) {
            user.setUserName(getSP().getString("userName", ""));
        }
        if (getSP().contains("userPassword")) {
            user.setUserPassword(getSP().getString("userPassword", ""));
        }
        if (getSP().contains("userGender")) {
            user.setUserGender(getSP().getString("userGender", ""));
        }
        if (getSP().contains("userCityId")) {
            user.setUserCityId(getSP().getString("userCityId", ""));
        }
        if (getSP().contains("userCity")) {
            user.setUserCity(getSP().getString("userCity", ""));
        }
        if (getSP().contains("userAge")) {
            user.setuserAge(getSP().getString("userAge", ""));
        }
        if (getSP().contains("userPosition")) {
            user.setUserPosition(getSP().getString("userPosition", ""));
        }
        if (getSP().contains("userImg")) {
            user.setUserImg(getSP().getString("userImg", ""));
        }
        if (getSP().contains("userEase")) {
            user.setUserEase(getSP().getString("userEase", ""));
        }
        if (getSP().contains("isKa")) {
            user.setIsKa(getSP().getString("isKa", ""));
        }
        if (getSP().contains("userTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("tag_id_" + i, ""));
                tag.setTagName(getSP().getString("tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserTags(tags);
        }
        if (getSP().contains("userCategoryTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userCategoryTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("category_tag_id_" + i, ""));
                tag.setTagName(getSP().getString("category_tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserCategoryTags(tags);
        }
        if (getSP().contains("userSkillsTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userSkillsTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("skill_tag_id_" + i, ""));
                tag.setTagName(getSP().getString("skill_tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserSkillsTags(tags);
        }
        if (getSP().contains("userPersonalTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userPersonalTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("personal_tag_id_" + i, ""));
                tag.setTagName(getSP().getString("personal_tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserPersonalTags(tags);
        }
    }

    @Override
    public UserInfo read() {
        UserInfo result = null;
        result = new UserInfo();
        read(result);
        return result;
    }

    @Override
    public void write(UserInfo user) {
        SharedPreferences.Editor editor = getSP().edit();
        if (!user.getUserId().equals("")) {
            editor.putString("userId", user.getUserId());
        }
        if (!user.getUserIdTemp().equals("")) {
            editor.putString("userIdTemp", user.getUserIdTemp());
        }
        if (!user.getUserPhone().equals("")) {
            editor.putString("userPhone", user.getUserPhone());
        }
        if (!user.getUserPhone().equals("")) {
            editor.putString("userCode", user.getUserCode());
        }
        if (!user.getUserName().equals("")) {
            editor.putString("userName", user.getUserName());
        }
        if (!user.getUserPassword().equals("")) {
            editor.putString("userPassword", user.getUserPassword());
        }

            editor.putString("userGender", user.getUserGender());

        if (!user.getUserCityId().equals("")) {
            editor.putString("userCityId", user.getUserCityId());
        }

            editor.putString("userCity", user.getUserCity());


            editor.putString("userAge", user.getuserAge());

        if (!user.getUserPosition().equals("")) {
            editor.putString("userPosition", user.getUserPosition());
        }
        if (!user.getUserImg().equals("")) {
            editor.putString("userImg", user.getUserImg());
        }
        if (!user.getUserEase().equals("")) {
            editor.putString("userEase", user.getUserEase());
        }
        if (!user.getIsKa().equals("")) {
            editor.putString("isKa", user.getIsKa());
        }
        if (user.getUserTags().size() > 0) {
            editor.putInt("userTag_size", user.getUserTags().size());
            for (int i = 0; i < user.getUserTags().size(); i++) {
                editor.putString("tag_id_" + i, user.getUserTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserTags().size(); i++) {
                editor.putString("tag_name_" + i, user.getUserTags().get(i).getTagName());
            }
        }
        if (user.getUserCategoryTags().size()>0) {
            editor.putInt("userCategoryTag_size", user.getUserCategoryTags().size());
            for (int i = 0; i < user.getUserCategoryTags().size(); i++) {
                editor.putString("category_tag_id_" + i, user.getUserCategoryTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserCategoryTags().size(); i++) {
                editor.putString("category_tag_name_" + i, user.getUserCategoryTags().get(i).getTagName());
            }
        }
        if (user.getUserSkillsTags().size() > 0) {
            editor.putInt("userSkillsTag_size", user.getUserSkillsTags().size());
            for (int i = 0; i < user.getUserSkillsTags().size(); i++) {
                editor.putString("skill_tag_id_" + i, user.getUserSkillsTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserSkillsTags().size(); i++) {
                editor.putString("skill_tag_name_" + i, user.getUserSkillsTags().get(i).getTagName());
            }
        }
        if (user.getUserPersonalTags().size() > 0) {
            editor.putInt("userPersonalTag_size", user.getUserPersonalTags().size());
            for (int i = 0; i < user.getUserPersonalTags().size(); i++) {
                editor.putString("personal_tag_id_" + i, user.getUserPersonalTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserPersonalTags().size(); i++) {
                editor.putString("personal_tag_name_" + i, user.getUserPersonalTags().get(i).getTagName());
            }
        }
        editor.commit();
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = getSP().edit();
        editor.clear();
        editor.commit();
    }
}