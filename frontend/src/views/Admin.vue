<template>
  <v-container>
    <div>
      <v-card color="grey lighten-5" flat min-height="50">
        <v-btn class="float-right" @click="showInput()" color="orange" text>import books</v-btn>
        <v-btn class="float-right" @click="exportBooks()" color="blue" text>export books</v-btn>
        <v-btn class="float-right" @click="deleteBooks" color="orange" text>delete</v-btn>
        <v-btn class="float-right" @click="dialogNew = true" color="blue" text>new</v-btn>
      </v-card>
      <v-file-input
        type="file"
        id="file"
        ref="file"
        @change="onFileChange($event)"
        v-show="inputIsShow"
        multiple
        label="File input"
      ></v-file-input>
    </div>

    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="books"
      :sort-by="['bookId']"
      :single-select="false"
      item-key="bookId"
      :sort-desc="[false, true]"
      show-select
      class="elevation-1"
    ></v-data-table>

    <v-snackbar v-model="snackbar" :multi-line="multiLine">
      {{ snackbarText }}
      <v-btn color="red" text @click="snackbar = false">Close</v-btn>
    </v-snackbar>
    <v-row justify="center">
      <v-dialog v-model="dialogNew" persistent max-width="600px">
        <v-card>
          <v-card-title>
            <span class="headline">New Book</span>
          </v-card-title>
          <v-card-text>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field
                      v-model="httpUrl"
                      :rules="[v => !!v || 'Book httpurl is required']"
                      label="Book httpurl*"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field
                      v-model="chiefEditor"
                      :rules="[v => !!v || 'Chief editor is required']"
                      label="Chief editor*"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6">
                    <v-select
                      v-model="access"
                      :rules="[v => !!v || 'Access is required']"
                      :items="['public', 'private']"
                      label="Access*"
                      required
                    ></v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialogNew = false">Close</v-btn>
            <v-btn color="blue darken-1" :disabled="!valid" text @click="submit">Submit</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogDelete" persistent max-width="600px">
        <v-card>
          <v-card-title>
            <span class="headline">Are you sure to delete following books?</span>
          </v-card-title>
          <v-card-text>
            <v-data-table :headers="headersDelete" :items="selected" class="elevation-1"></v-data-table>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialogDelete = false">Close</v-btn>
            <v-btn color="orange darken-1" :disabled="!valid" text @click="submitDelete">Delete</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      selected: [],
      valid: true,
      httpUrl: "",
      chiefEditor: "",
      access: "",
      books: [],
      dialogNew: false,
      dialogDelete: false,
      inputIsShow: false,
      snackbarText: "",
      multiLine: true,
      snackbar: false,
      file: "",
      bookId: "",
      headers: [
        {
          text: "book Id",
          align: "left",
          value: "bookId"
        },
        { text: "link", value: "httpUrl" },
        { text: "editor", value: "chiefEditor" },
        { text: "access", value: "access" }
      ],
      headersDelete: [
        {
          text: "book Id",
          align: "left",
          value: "bookId"
        },
        { text: "link", value: "httpUrl" }
      ]
    };
  },
  methods: {
    deleteBooks() {
      if (this.selected.length < 1) {
        this.snackbarText = "Please select the book you want to delete";
        this.snackbar = true;
        return;
      }
      this.dialogDelete = true;
    },
    submit() {
      if (this.$refs.form.validate()) {
        let json = {
          httpUrl: this.httpUrl,
          chiefEditor: this.chiefEditor,
          access: this.access
        };
        this.$http
          .post("/builder/addNewBook", json)
          .then(data => {
            this.snackbarText = data.data;
            this.snackbar = true;
          });
        this.dialogNew = false;
      }
    },
    submitDelete() {
      this.$http
        .post(
          "/builder/deleteBooks",
          this.selected
        )
        .then(data => {
          this.snackbarText = data.data;
          this.snackbar = true;
        });
      this.dialogDelete = false;
    },
    showInput() {
      this.inputIsShow = !this.inputIsShow;
    },
    async getAllBooks() {
      this.$http
        .get("/builder/exportBooks")
        .then(data => {
          this.books = data.data;
        });
    },
    download(filename, text) {
      var element = document.createElement("a");
      element.setAttribute(
        "href",
        "data:text/plain;charset=utf-8," + encodeURIComponent(text)
      );
      element.setAttribute("download", filename);

      element.style.display = "none";
      document.body.appendChild(element);

      element.click();

      document.body.removeChild(element);
    },
    onFileChange(e) {
      // eslint-disable-next-line no-console
      console.log(e[0]);
      const file = e[0];
      const formData = new FormData();
      formData.append("file", file, file.name);
      this.$http
        .post("/builder/importBooks", formData, {
          headers: {
            "Content-Type": "multipart/form-data;boundary=3634524314"
          }
        })
        .then(
          data => {
            this.snackbarText = data.data;
            this.snackbar = true;
          },
          data => {
            this.snackbarText = data.data;
            this.snackbar = true;
          }
        );
      this.getAllBooks();
    },
    exportBooks() {
      this.books.sort((book1, book2) => {
        return book1.bookId - book2.bookId;
      });
      this.download("books.json", JSON.stringify(this.books, null, 4));
    }
  },
  created() {
    this.getAllBooks();
  }
};
</script>

<style>
</style>