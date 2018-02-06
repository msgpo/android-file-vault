package com.vandenbreemen.mobilesecurestorage.android.mvp;

import android.Manifest;
import android.os.Environment;

import com.vandenbreemen.mobilesecurestorage.BuildConfig;
import com.vandenbreemen.mobilesecurestorage.android.FileSelectActivity;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.iterableWithSize;

/**
 * <h2>Intro
 * <p>
 * <h2>Other Details
 *
 * @author kevin
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FileSelectModelTest {

    private FileSelectModel sut;

    /**
     * App.  Using this to simulate having/not having permissions
     */
    private ShadowApplication app;

    private File subDir;


    @Before
    public void setup() throws Exception{

        FileSelectActivity activity = Robolectric.buildActivity(FileSelectActivity.class).get();
        app = Shadows.shadowOf(activity.getApplication());

        sut = new FileSelectModel(app.getApplicationContext());

        new File(Environment.getExternalStorageDirectory()+File.separator+"test").createNewFile();
        subDir = new File(Environment.getExternalStorageDirectory()+File.separator+"subdir");
        subDir.mkdir();
        new File(Environment.getExternalStorageDirectory()+File.separator+"subdir"
            +File.separator+"subFile").createNewFile();
    }

    @Test
    public void testListFiles(){
        app.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        assertFalse("Files list", CollectionUtils.isEmpty(sut.listFiles()));
    }

    @Test
    public void testListFileFailsIfNoPermission(){
        assertTrue("Files list empty", CollectionUtils.isEmpty(sut.listFiles()));
    }

    @Test
    public void testListDirectories(){
        app.grantPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        sut.setSelectDirectories(true);
        List<File> directories = sut.listFiles();

        assertThat(directories, allOf(
                iterableWithSize(1),
                hasItem(subDir)
        ));

    }

}
