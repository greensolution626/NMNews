package com.android.nmnewsagency.modelclass;

import java.io.Serializable;
import java.util.List;

public class SearchTopSearchModel {

    /**
     * Status : true
     * Message : Success
     * Data : {"Status":true,"Message":"Success","Data":{"PagedRecord":[{"Id":2,"Title":"#latest-news","Avatar":"","UserName":"","searchcount":5,"SearchType":"HASHTAG","UserId":""},{"Id":1,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":2,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":3,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":4,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":5,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":null,"UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":25,"Title":"@guttenagnath6","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgcQaI6AX-Zgg-5579VcyqDvGKbiGfeO76O4pZo1Q","UserName":"nagnath gutte ","searchcount":0,"SearchType":"PEOPLE","UserId":"85b779fe-fc80-4bc2-8302-8a0728d3ab5c"},{"Id":22,"Title":"@mrkhansalman545","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GjRBvrkEXcfSPEo0UvgfStpSh5zMZzG_BX-To4qIQ","UserName":"Salman Khan Amravati ","searchcount":0,"SearchType":"PEOPLE","UserId":"9879565d-668d-4d42-9061-9915cd711678"},{"Id":21,"Title":"@ketudeshpande06","Avatar":"null","UserName":"Vinayak Deshpande ","searchcount":0,"SearchType":"PEOPLE","UserId":"278450fa-0610-4631-9cb3-5e943c6bb33c"},{"Id":20,"Title":"@wwwbvishwanath5151","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgYjXZXnz-Gqd4-VaFiqkwv4dPErLvjrdkSBh_mtw","UserName":"vishwanath birajdar ","searchcount":0,"SearchType":"PEOPLE","UserId":"6700087b-ac68-4cd4-b804-80d3874d056f"},{"Id":19,"Title":"@rajushakthi911","Avatar":"https://lh3.googleusercontent.com/a-/AOh14Gj_0FMdQxzYpFV9PBJM2dBZgePfOiS9Uy_Xq8d35w","UserName":"RajuAlisha patel m ","searchcount":0,"SearchType":"PEOPLE","UserId":"a002ec51-090b-47d3-9f14-82d3621ee520"},{"Id":3,"Title":"#corona-news","Avatar":"","UserName":"","searchcount":0,"SearchType":"HASHTAG","UserId":""}],"TotalCount":0,"recordsTotal":12,"recordsFiltered":12,"Take":0,"Skip":0,"iTotalRecords":0,"iTotalDisplayRecords":0}}
     */

    private boolean Status;
    private String Message;
    private DataBeanX Data;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX implements Serializable {
        /**
         * Status : true
         * Message : Success
         * Data : {"PagedRecord":[{"Id":2,"Title":"#latest-news","Avatar":"","UserName":"","searchcount":5,"SearchType":"HASHTAG","UserId":""},{"Id":1,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":2,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":3,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":4,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":5,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":null,"UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":25,"Title":"@guttenagnath6","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgcQaI6AX-Zgg-5579VcyqDvGKbiGfeO76O4pZo1Q","UserName":"nagnath gutte ","searchcount":0,"SearchType":"PEOPLE","UserId":"85b779fe-fc80-4bc2-8302-8a0728d3ab5c"},{"Id":22,"Title":"@mrkhansalman545","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GjRBvrkEXcfSPEo0UvgfStpSh5zMZzG_BX-To4qIQ","UserName":"Salman Khan Amravati ","searchcount":0,"SearchType":"PEOPLE","UserId":"9879565d-668d-4d42-9061-9915cd711678"},{"Id":21,"Title":"@ketudeshpande06","Avatar":"null","UserName":"Vinayak Deshpande ","searchcount":0,"SearchType":"PEOPLE","UserId":"278450fa-0610-4631-9cb3-5e943c6bb33c"},{"Id":20,"Title":"@wwwbvishwanath5151","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgYjXZXnz-Gqd4-VaFiqkwv4dPErLvjrdkSBh_mtw","UserName":"vishwanath birajdar ","searchcount":0,"SearchType":"PEOPLE","UserId":"6700087b-ac68-4cd4-b804-80d3874d056f"},{"Id":19,"Title":"@rajushakthi911","Avatar":"https://lh3.googleusercontent.com/a-/AOh14Gj_0FMdQxzYpFV9PBJM2dBZgePfOiS9Uy_Xq8d35w","UserName":"RajuAlisha patel m ","searchcount":0,"SearchType":"PEOPLE","UserId":"a002ec51-090b-47d3-9f14-82d3621ee520"},{"Id":3,"Title":"#corona-news","Avatar":"","UserName":"","searchcount":0,"SearchType":"HASHTAG","UserId":""}],"TotalCount":0,"recordsTotal":12,"recordsFiltered":12,"Take":0,"Skip":0,"iTotalRecords":0,"iTotalDisplayRecords":0}
         */

        private boolean Status;
        private String Message;
        private DataBean Data;

        public boolean isStatus() {
            return Status;
        }

        public void setStatus(boolean Status) {
            this.Status = Status;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public DataBean getData() {
            return Data;
        }

        public void setData(DataBean Data) {
            this.Data = Data;
        }

        public static class DataBean implements Serializable {
            /**
             * PagedRecord : [{"Id":2,"Title":"#latest-news","Avatar":"","UserName":"","searchcount":5,"SearchType":"HASHTAG","UserId":""},{"Id":1,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":2,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":3,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":4,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":"xyz.jpg","UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":5,"Title":"तहसील में कोरोना के 31 नए मरीज मिले तो23 मरीज  ठीक हो कर घर लौटे","Avatar":null,"UserName":"admin@admin.com","searchcount":1,"SearchType":"VIDEO","UserId":"6fc00a95-dc1f-473d-b324-a990a9543498"},{"Id":25,"Title":"@guttenagnath6","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgcQaI6AX-Zgg-5579VcyqDvGKbiGfeO76O4pZo1Q","UserName":"nagnath gutte ","searchcount":0,"SearchType":"PEOPLE","UserId":"85b779fe-fc80-4bc2-8302-8a0728d3ab5c"},{"Id":22,"Title":"@mrkhansalman545","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GjRBvrkEXcfSPEo0UvgfStpSh5zMZzG_BX-To4qIQ","UserName":"Salman Khan Amravati ","searchcount":0,"SearchType":"PEOPLE","UserId":"9879565d-668d-4d42-9061-9915cd711678"},{"Id":21,"Title":"@ketudeshpande06","Avatar":"null","UserName":"Vinayak Deshpande ","searchcount":0,"SearchType":"PEOPLE","UserId":"278450fa-0610-4631-9cb3-5e943c6bb33c"},{"Id":20,"Title":"@wwwbvishwanath5151","Avatar":"https://lh3.googleusercontent.com/a-/AOh14GgYjXZXnz-Gqd4-VaFiqkwv4dPErLvjrdkSBh_mtw","UserName":"vishwanath birajdar ","searchcount":0,"SearchType":"PEOPLE","UserId":"6700087b-ac68-4cd4-b804-80d3874d056f"},{"Id":19,"Title":"@rajushakthi911","Avatar":"https://lh3.googleusercontent.com/a-/AOh14Gj_0FMdQxzYpFV9PBJM2dBZgePfOiS9Uy_Xq8d35w","UserName":"RajuAlisha patel m ","searchcount":0,"SearchType":"PEOPLE","UserId":"a002ec51-090b-47d3-9f14-82d3621ee520"},{"Id":3,"Title":"#corona-news","Avatar":"","UserName":"","searchcount":0,"SearchType":"HASHTAG","UserId":""}]
             * TotalCount : 0
             * recordsTotal : 12
             * recordsFiltered : 12
             * Take : 0
             * Skip : 0
             * iTotalRecords : 0
             * iTotalDisplayRecords : 0
             */

            private int TotalCount;
            private int recordsTotal;
            private int recordsFiltered;
            private int Take;
            private int Skip;
            private int iTotalRecords;
            private int iTotalDisplayRecords;
            private List<PagedRecordBean> PagedRecord;

            public int getTotalCount() {
                return TotalCount;
            }

            public void setTotalCount(int TotalCount) {
                this.TotalCount = TotalCount;
            }

            public int getRecordsTotal() {
                return recordsTotal;
            }

            public void setRecordsTotal(int recordsTotal) {
                this.recordsTotal = recordsTotal;
            }

            public int getRecordsFiltered() {
                return recordsFiltered;
            }

            public void setRecordsFiltered(int recordsFiltered) {
                this.recordsFiltered = recordsFiltered;
            }

            public int getTake() {
                return Take;
            }

            public void setTake(int Take) {
                this.Take = Take;
            }

            public int getSkip() {
                return Skip;
            }

            public void setSkip(int Skip) {
                this.Skip = Skip;
            }

            public int getITotalRecords() {
                return iTotalRecords;
            }

            public void setITotalRecords(int iTotalRecords) {
                this.iTotalRecords = iTotalRecords;
            }

            public int getITotalDisplayRecords() {
                return iTotalDisplayRecords;
            }

            public void setITotalDisplayRecords(int iTotalDisplayRecords) {
                this.iTotalDisplayRecords = iTotalDisplayRecords;
            }

            public List<PagedRecordBean> getPagedRecord() {
                return PagedRecord;
            }

            public void setPagedRecord(List<PagedRecordBean> PagedRecord) {
                this.PagedRecord = PagedRecord;
            }

            public static class PagedRecordBean implements Serializable {
                /**
                 * Id : 2
                 * Title : #latest-news
                 * Avatar :
                 * UserName :
                 * searchcount : 5
                 * SearchType : HASHTAG
                 * UserId :
                 */

                private int Id;
                private String Title;
                private String Avatar;
                private String UserName;
                private int searchcount;
                private String SearchType;
                private String UserId;

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public String getAvatar() {
                    return Avatar;
                }

                public void setAvatar(String Avatar) {
                    this.Avatar = Avatar;
                }

                public String getUserName() {
                    return UserName;
                }

                public void setUserName(String UserName) {
                    this.UserName = UserName;
                }

                public int getSearchcount() {
                    return searchcount;
                }

                public void setSearchcount(int searchcount) {
                    this.searchcount = searchcount;
                }

                public String getSearchType() {
                    return SearchType;
                }

                public void setSearchType(String SearchType) {
                    this.SearchType = SearchType;
                }

                public String getUserId() {
                    return UserId;
                }

                public void setUserId(String UserId) {
                    this.UserId = UserId;
                }
            }
        }
    }
}
