package com.comersss.modeltwo;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextUtils {
    /*
     * 专门用于edittext输入处理的工具类
     */
    // 设置edittext只能输入金额
    public static void setPriceEditText(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
//                if (!TextUtils.isEmpty(s.toString())) {
//                    double sumText = Double.parseDouble(s.toString());
//                    if (sumText > maxDouble) {
//                        s = s.toString().subSequence(0, s.toString().length() - 1);
//                        editText.setText(s);
//                        editText.setSelection(s.length());
//                        return;
//                    }
//                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }

    public static boolean isHaveValue(final EditText editText) {
        return editText.getEditableText() != null && !editText.getEditableText().toString().equals("");
    }


}
