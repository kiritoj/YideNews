package com.Blinger.YiDeNews.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.Blinger.base.utils.StringUtils;
import com.Blinger.YiDeNews.App;
import com.Blinger.YiDeNews.dao.NewTypeBeanDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * 作者：310Lab
 * 时间：2020/4/2 13:55
 * 邮箱：1760567382@qq.com
 * 功能：
 * id:1 title头条 des:top
 * id:2 title社会 des:shehui
 * id:3 title国内 des:guonei
 * id:9 title健康 des:jiankang
 * id:4 title国际 des:guoji
 * id:6 title军事 des:junshi
 * id:5 title体育 des:tiyu
 * id:8 title财经 des:caijing
 * id:10 title汽车 des:qiche
 * id:7 title科技 des:keji
 */
@Entity
public class NewTypeBean implements Parcelable
{
    @Id
    private Long id;
    private String title;
    private String descript;

    @Generated(hash = 327160687)
    public NewTypeBean(Long id, String title, String descript)
    {
        this.id = id;
        this.title = title;
        this.descript = descript;
    }

    @Generated(hash = 812807674)
    public NewTypeBean()
    {
    }

    protected NewTypeBean(Parcel in)
    {
        if (in.readByte() == 0)
        {
            id = null;
        } else
        {
            id = in.readLong();
        }
        title = in.readString();
        descript = in.readString();
    }

    public static final Creator<NewTypeBean> CREATOR = new Creator<NewTypeBean>()
    {
        @Override
        public NewTypeBean createFromParcel(Parcel in)
        {
            return new NewTypeBean(in);
        }

        @Override
        public NewTypeBean[] newArray(int size)
        {
            return new NewTypeBean[size];
        }
    };

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescript()
    {
        return this.descript;
    }

    public void setDescript(String descript)
    {
        this.descript = descript;
    }


    /**
     * Get the New Type List from db or txt file
     *
     * @return The list of New Type Bean
     */
    public static List<NewTypeBean> getNewTypeList()
    {
        NewTypeBeanDao dao = App.mSession.getNewTypeBeanDao();
        List<NewTypeBean> list = dao.loadAll();
        //头条,社会,国内,国际,体育,军事,科技,财经,健康,汽车,法制
        if (list == null || list.isEmpty())
        {
            String json = StringUtils.getJson(App.getContent(), "NewType.json");
            Gson gson = new Gson();
            list = gson.fromJson(json, new TypeToken<List<NewTypeBean>>()
            {
            }.getType());
            dao.insertInTx(list);
        }
        return list;
    }


    /**
     * Get the New Type Data from db or txt file
     *
     * @param title The chinese of the New Type
     * @return New Type
     */
    public static NewTypeBean getNewTypeBean(String title)
    {
        if (TextUtils.isEmpty(title))
        {
            return null;
        }
        List<NewTypeBean> list = getNewTypeList();
        for (NewTypeBean data : list)
        {
            if (data.getTitle().equals(title))
            {
                return data;
            }
        }
        return null;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        if (id == null)
        {
            parcel.writeByte((byte) 0);
        } else
        {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(title);
        parcel.writeString(descript);
    }
}
